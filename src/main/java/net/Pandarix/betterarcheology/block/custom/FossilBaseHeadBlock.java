package net.Pandarix.betterarcheology.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.text.Text;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.List;

public class FossilBaseHeadBlock extends HorizontalFacingBlock
{
    public static final MapCodec<FossilBaseHeadBlock> CODEC = createCodec(FossilBaseHeadBlock::new);

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec()
    {
        return CODEC;
    }

    public static DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public FossilBaseHeadBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        return VoxelShapes.empty();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    //used to give all fossil blocks their own tooltip
    //gets blocks translationkey itself and appends "_tooltip" to get the xyz_tooltip lang content
    //also appends the [2/2] indicator for a set
    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options)
    {
        tooltip.add(Text.translatable(this.getTranslationKey() + "_tooltip").formatted(Formatting.GRAY).append(Text.translatable("block.betterarcheology.fossil_head_set").formatted(Formatting.BLUE)));
        super.appendTooltip(stack, context, tooltip, options);
    }

    public BlockState rotate(BlockState state, BlockRotation rotation)
    {
        return (BlockState) state.with(FACING, rotation.rotate((Direction) state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror)
    {
        return state.rotate(mirror.getRotation((Direction) state.get(FACING)));
    }
}
